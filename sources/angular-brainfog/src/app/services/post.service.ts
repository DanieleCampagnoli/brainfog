import { Injectable } from '@angular/core';
import {BrainfogPost} from '../entities/brainfog.post';
import {BrainfogRoutes} from '../app.constants';
/**
 * service responsible to provide the blog posts data to the application.
 */
@Injectable({
  providedIn: 'root'
})
export class PostService {

  private  posts: Array<BrainfogPost>=[];

  private nextPostId:number=0;


  constructor() {

    //dummy posts
    for(let i=1;i<20;i++){
       let id=this.getNextPostiId();
       let title="title";
       let imageUrl="/assets/images/test1.jpg";
       let  createDate= new Date("2022-01-07");
       let postRoute=BrainfogRoutes.POST_BASE_ROUTE;
       let post= new BrainfogPost(id,title,imageUrl,createDate,postRoute);
       this.posts.push(post);
    }
    //pasa apod voronoi post
    let title="Voronoi On Nasa APOD";
    let imageUrl="/assets/images/nasa-apod-voronoi.png";
    let createDate= new Date("2022-01-07");
    let postRoute=BrainfogRoutes.POST_NASA_APOD_BASE_ROUTE;
    let postNasaApodVoronoi= new BrainfogPost(this.getNextPostiId(),title,imageUrl,createDate,postRoute);
    this.posts.push(postNasaApodVoronoi);
  }

  private getNextPostiId():number{
    this.nextPostId++;
    return this.nextPostId;
  }

  public getAllPosts():Array<BrainfogPost>{
    let postsCopy= new Array<BrainfogPost>();
    this.posts.forEach(post => postsCopy.push(post));
    return postsCopy;
  }

  public getPost(id: number):BrainfogPost {
    let post=this.posts.find(post => post.id==id);
    if(post==null){
      throw new Error('Post Not Found');
    }
    return post;
  }

}
