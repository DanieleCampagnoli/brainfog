
/**
 * this entity represents a blog post.
 */
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
  *  the react route url of the post
  * */
 public postRoute: string;

 /**
  * create a new post.
  */
 public constructor (id: number, title: String,
   imageUrl: String,
   createDate: Date,
   postRoute: string){
   this.id=id;
   this.title=title;
   this.imageUrl=imageUrl;
   this.createDate=createDate;
   this.postRoute=postRoute;
 }
};
