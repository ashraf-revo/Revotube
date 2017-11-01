import {Component, NgZone, OnDestroy, OnInit} from "@angular/core";
import {UploadService} from "../../services/upload.service";
import {Media} from "../../domain/media";
import {Subscription} from "rxjs";
import {Upload} from "../../domain/upload";

@Component({
  selector: 'rt-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit, OnDestroy {
  public media: Media = new Media();
  public uploading: Upload[] = [];
  public subscription: Subscription;

  constructor(private _uploadService: UploadService, private zone: NgZone,) {

  }

  ngOnInit() {
    this.subscription = this._uploadService.getUploading().subscribe((u: Upload) => {
      this.zone.run(() => {
        let v = this.exist(u);
        if (v != -1) {
          if (u.state == "fail") {
            this.uploading[v].state = "fail"
          }
          else
            this.uploading[v] = u;

        }
        else
          this.uploading.push(u)
      });
    });
  }

  exist(upload: Upload): number {
    let v = -1;
    for (let i = 0; i < this.uploading.length; i++) {
      if (this.uploading[i].timeId == upload.timeId) {
        v = i;
        break;
      }
    }

    return v;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe()

  }

  upload(file: any, fileView: any, meta: any) {
    if (file.files.length > 0) {
      this.media.file = file.files[0];
    }
    this._uploadService.upload(this.media).subscribe();
    this.reset(file, fileView, meta)
  }

  reset(file: any, fileView: any, meta: any) {
    if (file.files.length > 0) {
      file.files = null;
    }
    fileView.value = "";
    meta.value = "";
    this.media = new Media();
  }


  name(ref1: any, ref2: any, ref3: any) {
    if (ref1.files.length == 1) {
      ref2.value = ref1.files[0].name;
    } else {
      ref2.value = '';
    }
    this.media.title = ref2.value;
    this.media.meta = ref3.value;
  }
}
