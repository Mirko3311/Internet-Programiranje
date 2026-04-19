import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Uzimamo token koji smo spasili prilikom logina
  const token = localStorage.getItem('token');

  // Ako token postoji, kloniramo zahtjev i dodajemo mu Authorization header
  /*
  Interceptor "hvata" zahtjev: 
  - Vidi da token postoji
  - Pravi kopiju(cloned) originalnog zahtjeva, ali dodaje Authorization header sa tokenom
  - Prosljeđuje taj klonirani zahtjev dalje (next(cloned))
    Na taj način, svaki zahtjev koji ide prema backendu će imati Authorization header sa JWT tokenom, osim onih zahtjeva gdje token ne postoji (npr. login zahtjev)

  */
 console.log("Interceptor uhvatio zahtjev za: " + req.url);
  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(cloned);
  }

  // Ako nema tokena (npr. na login stranici), samo proslijedi originalni zahtjev
  return next(req);
};