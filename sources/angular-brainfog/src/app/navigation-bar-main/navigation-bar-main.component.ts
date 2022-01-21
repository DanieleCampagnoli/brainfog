import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BrainfogRoutes } from '../app.constants';

@Component({
  selector: 'app-navigation-bar-main',
  templateUrl: './navigation-bar-main.component.html',
  styleUrls: ['./navigation-bar-main.component.css']
})
export class NavigationBarMainComponent implements OnInit {

  constructor(private router: Router ) { }

  ngOnInit(): void {
  }

  homeClicked(): void{
    this.router.navigateByUrl(BrainfogRoutes.POST_LIST);
  }

}
