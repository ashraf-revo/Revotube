export class User {
  id: number;
  name: string;
  imageUrl: string;
  phone: string;
  info: string;
  email: string;
  createdDate: Date;
  locked: boolean;
  enable: boolean;
  enabled: boolean;
  username: string;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  authorities:Array<any>
}
