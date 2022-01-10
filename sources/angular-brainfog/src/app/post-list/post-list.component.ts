import { Component, OnInit } from '@angular/core';
import { BrainfogPost } from '../entities/brainfog.post';
import { PostService } from '../services/post.service';
import {Router} from '@angular/router';
import { BrainfogRoutes } from '../app.constants';
@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
  posts?: Array<BrainfogPost>;

  constructor(private postService:PostService,private router: Router ) { }

  ngOnInit(): void {
    //initialize the post list when the compoent is instantiated
    this.posts=this.postService.getAllPosts();
  }

  postClicked(post:BrainfogPost): void{
    this.router.navigateByUrl(BrainfogRoutes.POST);
  }
}
