import {Injectable} from "@angular/core";
import {Observable, Observer, Subject, ReplaySubject} from "rxjs";
import {Upload} from "../domain/upload";
import {Media} from "../domain/media";

@Injectable()
export class UploadService {
  private uploadingSubject: Subject<Upload> = new ReplaySubject<Upload>();
  private url: string = "/tube/api/";

  constructor() {
  }

  upload(media: Media): Observable<Upload> {
    let timeId: number = new Date().getTime();
    return Observable.create((observer: Observer<Upload>) => {
      let form: FormData = new FormData();
      let xhr: XMLHttpRequest = new XMLHttpRequest();

      if (media.title != null) form.append("title", media.title);
      if (media.file != null) form.append("file", media.file);

      xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            let copyUpload: Upload = this.CopyUpload(media, timeId, 100, "completed");
            observer.next(copyUpload);
            this.uploadingSubject.next(copyUpload);
            observer.complete();
          } else {
            let copyUpload: Upload = this.CopyUpload(media, timeId, 0, "fail");
            this.uploadingSubject.next(copyUpload);
            observer.next(copyUpload);
          }
        }
        else {
          let copyUpload: Upload = this.CopyUpload(media, timeId, 0, "fail");
          this.uploadingSubject.next(copyUpload);
          observer.next(copyUpload);
        }
      };

      xhr.upload.onprogress = (event) => {
        let value: number = Math.round(event.loaded / event.total * 100);
        if (value != 100)
          this.uploadingSubject.next(this.CopyUpload(media, timeId, value, "binding"));
      };
      xhr.open('POST', this.url+"save", true);
      let cookies: string[] = document.cookie.split(";");
      for (let i = 0; i < cookies.length; i++)
        if (cookies[i].indexOf("XSRF-TOKEN=") != -1) xhr.setRequestHeader("X-XSRF-TOKEN", cookies[i].replace("XSRF-TOKEN=", ""));
      xhr.setRequestHeader("enctype", "multipart/form-data");

      // IE bug fixes to clear cache
      xhr.setRequestHeader("Cache-Control", "no-cache");
      xhr.setRequestHeader("Cache-Control", "no-store");
      xhr.setRequestHeader("Pragma", "no-cache");
      xhr.send(form);
    });
  }

  private CopyUpload(media: Media, timeId: number, value: number, state: string): Upload {
    let uploadObj: Upload = new Upload();
    uploadObj.timeId = timeId;
    uploadObj.data = media;
    uploadObj.value = value;
    uploadObj.state = state;
    return uploadObj;
  }

  getUploading(): Subject<Upload> {
    return this.uploadingSubject;
  }
}
