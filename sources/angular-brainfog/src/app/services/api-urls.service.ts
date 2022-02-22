import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {BrainFogApiEndpoints} from '../app.constants';
/**
 * low level service that provides the rest api urls to the application.
 */
@Injectable({
  providedIn: 'root'
})
export class ApiUrlsService {

  constructor() { }

  public getNasaAPODVoronoiApiUrl():string{
    return environment.apiUrl+BrainFogApiEndpoints.NASA_APOD_VORONOI;
  }
}
