import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SecuredComponent } from './secured.component';
import { LayoutComponent } from './layout/layout.component';
import { ProfileComponent } from './profile/profile.component';
import { SecuredRoutingModule } from './secured-routing.module';
import { FeedComponent } from './feed/feed.component';
import { ExploreComponent } from './explore/explore.component';
import { SharedModule } from './shared/shared.module';

const components = [SecuredComponent, LayoutComponent, ProfileComponent];

@NgModule({
  imports: [
    CommonModule,
    SecuredRoutingModule,
    SharedModule
  ],
  declarations: [components, FeedComponent, ExploreComponent],
  exports: [components]
})
export class SecuredModule { }