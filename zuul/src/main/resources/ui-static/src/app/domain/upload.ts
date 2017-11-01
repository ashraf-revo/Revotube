import {Media} from "./media";
export class Upload {
  data: Media;
  value: number = 0;
  timeId: number = 0;
  state: string = "binding";

  width(): string {
    return this.value + "%";
  }
}
