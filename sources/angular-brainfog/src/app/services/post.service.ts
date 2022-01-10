import { Injectable } from '@angular/core';
import {BrainfogPost} from '../entities/brainfog.post';
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
       let post= new BrainfogPost(id,title,imageUrl,createDate);
       this.posts.push(post);
    }
  }

  public getAllPosts():Array<BrainfogPost>{
    let postsCopy= new Array<BrainfogPost>();
    this.posts.forEach(post => postsCopy.push(post));
    return postsCopy;
  }
}
