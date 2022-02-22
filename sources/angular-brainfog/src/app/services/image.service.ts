import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, concat,fromEvent } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
/**
 * service that offers various API to work with images.
 */
@Injectable({
  providedIn: 'root'
})
export class ImageService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private httpClient: HttpClient) { }
  /**
   * utility method to download an image programmatically
   *
   * @param imageUrl  the image
   *
   * @returns  the observable
   */
  public downloadImage(imageUrl:string): Observable<string | ArrayBuffer | null>{
   //dowload the image
   let imageObservable= this.httpClient.get(imageUrl, { responseType: 'blob' });

   //helper class that is able to handle files
   let reader= new FileReader();
   const observable = new Observable<string | ArrayBuffer | null>(subscriber => {
    reader.addEventListener('load',()=>{
      subscriber.next(reader.result);
      subscriber.complete();
    });
  });
   //parse the image
   let parsedImageObservable= imageObservable.pipe(switchMap(data =>{
     reader.readAsDataURL(data);
     return observable;
    }));

    //return an observable than can be used by the client
    return parsedImageObservable;
  }

}
