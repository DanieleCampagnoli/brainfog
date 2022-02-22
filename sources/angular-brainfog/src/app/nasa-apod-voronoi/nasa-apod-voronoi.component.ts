import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BrainfogPost } from '../entities/brainfog.post';
import { ApiUrlsService } from '../services/api-urls.service';
import { ImageService } from '../services/image.service';
import { PostService } from '../services/post.service';

@Component({
  selector: 'app-nasa-apod-voronoi',
  templateUrl: './nasa-apod-voronoi.component.html',
  styleUrls: ['./nasa-apod-voronoi.component.css']
})
export class NasaApodVoronoiComponent implements OnInit {

  post: BrainfogPost;

  imageDownloadPending:boolean;

  downloadedImage?:string | ArrayBuffer | null;


  constructor(private route: ActivatedRoute, private postService:PostService,private imageService:ImageService,private apiUrlsService:ApiUrlsService) {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.post=this.postService.getPost(id);
    this.imageDownloadPending=true;


    imageService.downloadImage(apiUrlsService.getNasaAPODVoronoiApiUrl()).subscribe((image) => {
      this.downloadedImage = image;
      this.imageDownloadPending=false;
   });
   }

  ngOnInit(): void {

  }

}
