import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BrainfogRoutes} from './app.constants';
import { PostListComponent } from './post-list/post-list.component';
import { PostComponent } from './post/post.component';

const routes: Routes = [
  /**
   * default route.
   */
   { path: '', redirectTo: BrainfogRoutes.POST_LIST, pathMatch: 'full' },
   { path: BrainfogRoutes.POST_LIST, component: PostListComponent },
   { path: BrainfogRoutes.POST, component: PostComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
