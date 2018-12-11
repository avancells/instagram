import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import {UserService} from './service/user.service';
import { authService } from './service/auth.service';
import { FollowService } from './service/follow.service';
import { AuthGuard } from './shared';
import { PostService } from './service/post.service';
import { CommentService } from './service/CommentService';
import { LikeCommentService } from './service/LikeComment';
import { LikeService } from './service/LikeService';
import { NotificationService } from './service/notification.service';

// AoT requires an exported function for factories
export const createTranslateLoader = (http: HttpClient) => {
    /* for development
    return new TranslateHttpLoader(
        http,
        '/start-angular/SB-Admin-BS4-Angular-6/master/dist/assets/i18n/',
        '.json'
    ); */
    return new TranslateHttpLoader(http, './assets/i18n/', '.json');
};

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient]
            }
        }),
        AppRoutingModule
    ],
    declarations: [AppComponent],
    providers: [
        authService,
        UserService,
        FollowService,
        AuthGuard,
        PostService,
        CommentService,
        LikeCommentService,
        LikeService,
        NotificationService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {}
