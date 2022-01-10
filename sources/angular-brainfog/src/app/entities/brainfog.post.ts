

export class BrainfogPost {
 /**
  * a sort of buiness key of the post.
  */
 public id: number;
 /**
  * the title of the post.
  */
 public title: String;
 /**
  * the image path.
  */
 public imageUrl: String;
 /**
  * the create date.
  */
 public createDate: Date;
 /**
  * create a new post.
  */
 public constructor (id: number, title: String,
   imageUrl: String,
   createDate: Date){
   this.id=id;
   this.title=title;
   this.imageUrl=imageUrl;
   this.createDate=createDate;
 }
};
