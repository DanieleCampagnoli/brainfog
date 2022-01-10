import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarMainComponent } from './navigation-bar-main/navigation-bar-main.component';
import { PostListComponent } from './post-list/post-list.component';
import { PostComponent } from './post/post.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarMainComponent,
    PostListComponent,
    PostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
