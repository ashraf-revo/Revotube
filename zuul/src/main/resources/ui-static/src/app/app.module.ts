import {VgStreamingModule} from 'videogular2/streaming';
import {VgCoreModule} from 'videogular2/core';
import {VgControlsModule} from 'videogular2/controls';
import {VgOverlayPlayModule} from 'videogular2/overlay-play';
import {VgBufferingModule} from 'videogular2/buffering';
import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {RouterModule} from "@angular/router";
import {routes} from "./services/routes";
import {BaseComponent} from "./views/base/base.component";
import {HomeComponent} from "./views/home/home.component";
import {ErrorComponent} from "./views/error/error.component";
import {TubeService} from "./services/tube.service";
import {DefaultService} from "./services/default.service";
import {UserService} from "./services/user.service";
import {AuthService} from "./services/auth.service";
import {DiscoverComponent} from "./views/discover/discover.component";
import {VideoComponent} from "./views/video/video.component";
import {ProfileComponent} from "./views/profile/profile.component";
import {SettingsComponent} from "./views/settings/settings.component";
import {UploadComponent} from "./views/upload/upload.component";
import {UploadService} from "./services/upload.service";
import {HeaderComponent} from './componant/header/header.component';
import {NavComponent} from './componant/nav/nav.component';
import {VideoGridComponent} from './componant/video-grid/video-grid.component';
import {VideoRowComponent} from './componant/video-row/video-row.component';
import {AnimateBarComponent} from './componant/animate-bar/animate-bar.component';
import {NotificationComponent} from './componant/notification/notification.component';
import {FeedbackService} from "./services/feedback.service";
import {SearchComponent} from "./views/search/search.component";


@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    BaseComponent,
    HomeComponent,
    ErrorComponent,
    DiscoverComponent,
    VideoComponent,
    ProfileComponent,
    SettingsComponent,
    UploadComponent,
    HeaderComponent,
    NavComponent,
    VideoGridComponent,
    VideoRowComponent,
    AnimateBarComponent,
    NotificationComponent,

  ],
  imports: [VgStreamingModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    BrowserModule,
    FormsModule,
    HttpModule, RouterModule.forRoot(routes)
  ],
  providers: [DefaultService, TubeService, UserService, AuthService, UserService, UploadService, FeedbackService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
