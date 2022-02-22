/**
 * routes of the angular application.
 */
export abstract class BrainfogRoutes{
  static readonly POST_LIST="posts";
  static readonly POST_BASE_ROUTE="post";
  static readonly POST=BrainfogRoutes.POST_BASE_ROUTE+"/:id";
  static readonly POST_NASA_APOD_BASE_ROUTE="nasa/apod/voronoi";
  static readonly POST_NASA_APOD=BrainfogRoutes.POST_NASA_APOD_BASE_ROUTE+"/:id";
};
/**
 * Web API endpoints.
 */
export abstract class BrainFogApiEndpoints{
   static readonly NASA_APOD_VORONOI="/nasa/apod/voronoi";
}
