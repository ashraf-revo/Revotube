package org.revo.Service.Impl;

import com.comcast.viper.hlsparserj.PlaylistFactory;
import com.comcast.viper.hlsparserj.PlaylistVersion;
import com.comcast.viper.hlsparserj.tags.UnparsedTag;
import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.FrameGrab;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.NIOUtils;
import org.revo.Config.CustomProcessor;
import org.revo.Domain.Media;
import org.revo.Domain.Status;
import org.revo.Repository.MediaRepository;
import org.revo.Service.MediaService;
import org.revo.Service.S3Service;
import org.revo.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by ashraf on 15/04/17.
 */
@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private CustomProcessor customProcessor;

    @Override
    public File saveInFileSystem(Media media) throws IOException {
        Path tempFile = Files.createTempFile("", media.getFile().getOriginalFilename());
        tempFile.toFile().delete();
        Files.copy(media.getFile().getInputStream(), tempFile);
        return tempFile.toFile();
    }

    @Override
    public Media saveFile(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public Media save(Media media) {
        Media one = mediaRepository.findOne(media.getId());

        one.setM3u8(media.getM3u8());
        one.setTime(media.getTime());
        one.setStatus(media.getStatus());
        one.setSecret(media.getSecret());

        Media saved = mediaRepository.save(one);
        this.customProcessor.custom_output().send(MessageBuilder.withPayload(saved).build());
        return saved;
    }

    @Override
    public List<Media> findAll(Status status) {
        return mediaRepository.findByStatus(status).stream().map(it -> {
            it.setSecret(null);
            it.setM3u8(null);
            return it;
        }).collect(toList());
    }

    @Override
    public Media findOne(String id) {
        return mediaRepository.findOne(id);
    }

    @Override
    public String findOneParsed(String id) {
        List<UnparsedTag> tags = PlaylistFactory.parsePlaylist(PlaylistVersion.TWELVE, findOne(id).getM3u8()).getTags();
        return Utils.TOString(tags, s -> s3Service.generateTsUrl(s));
    }

    @Override
    public String lastId() {
        return mediaRepository.findTopByOrderByIdDesc().getId();
    }

    @Override
    public List<Media> findByUser(String id, Status status) {
        return mediaRepository.findByUserIdAndStatus(id, status);
    }

    /*
        @Override
        public Media publish(Media media, User user) {
            mediaRepository.publish(media.getId(), user.getId());
            return mediaRepository.findOne(media.getId());
        }
    */
    public File convertImage(File video) {
        try {
            FileChannelWrapper ch = NIOUtils.readableFileChannel(video);
            FrameGrab fg = new FrameGrab(ch);
            String type = "png";
            String vs = video.toString();
            File output = new File(vs.substring(0, vs.indexOf(".")) + "." + type);
            ImageIO.write(fg.getFrame(), type, output);
            NIOUtils.closeQuietly(ch);
            return output;
        } catch (IOException | JCodecException ignored) {
            return null;
        }
    }

    @Override
    public Media process(Media media) throws IOException {
        File inFileSystem = saveInFileSystem(media);
        File image = convertImage(inFileSystem);

        Media save = saveFile(media);
        if (image != null) {
            save.setImage(s3Service.pushImage(media.getId() + ".png", image));
            saveFile(save);
            image.delete();
        }
        new Thread(() -> {
            System.out.println("will sart");
            s3Service.pushMedia(save.getId(), inFileSystem);
            System.out.println("uploaded " + save.getId());
            inFileSystem.delete();
            Media m = new Media();
            m.setId(save.getId());
            System.out.println("will send");
            customProcessor.output().send(MessageBuilder.withPayload(m).build());
            System.out.println("done thread");
        }).start();

        return save;
    }
}
