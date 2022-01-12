import { Injectable } from '@angular/core';
import {BrainfogPost} from '../entities/brainfog.post';
import {BrainfogRoutes} from '../app.constants';
@Injectable({
  providedIn: 'root'
})
export class PostService {

  private  posts: Array<BrainfogPost>=[];

  constructor() {
    for(let i=1;i<20;i++){
       let id=1
       let title="title";
       let imageUrl="/assets/images/test1.jpg";
       let  createDate= new Date("2022-01-07");
       let postRoute=BrainfogRoutes.POST_BASE_ROUTE;
       let post= new BrainfogPost(id,title,imageUrl,createDate,postRoute);
       this.posts.push(post);
    }
  }

  public getAllPosts():Array<BrainfogPost>{
    let postsCopy= new Array<BrainfogPost>();
    this.posts.forEach(post => postsCopy.push(post));
    return postsCopy;
  }

  public getPost(id: number):BrainfogPost {

    for (var i = 0; i < this.posts.length; i++) {
         return this.posts[i];
     }
     throw new Error('getPost: post id not found '+id);
  }
}
