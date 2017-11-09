import {User} from "./user";
import {MediaInfo} from "./media-info";

export class Media {
  id: number;
  m3u8: string;
  image: string;
  mediaInfo: MediaInfo;
  meta: string;
  time: number;
  userId: number;
  Status: string;
  createdDate: string;
  title: string;
  file: any;
  user: User;
}
