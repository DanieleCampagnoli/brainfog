import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BrainfogPost } from '../entities/brainfog.post';
import { PostService } from '../services/post.service';
@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  post: BrainfogPost;

  constructor(private route: ActivatedRoute, private postService:PostService) {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.post=this.postService.getPost(id);
   }

  ngOnInit(): void {

  }

}
