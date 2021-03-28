import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler, HttpEvent,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {OidcSecurityService} from 'angular-auth-oidc-client';
import {Observable} from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private secureRoutes = ['https://localhost:5001/api'];

  constructor(private authService: OidcSecurityService) {}

  // FIXME: This should NOT be sent in every request
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.secureRoutes.find((x) => request.url.startsWith(x))) {
      return next.handle(request);
    }

    const token = this.authService.getIdToken();

    if (!token) {
      return next.handle(request);
    }

    request = request.clone({
      headers: request.headers.set('Authorization', 'Bearer ' + token),
    });

    return next.handle(request);
  }
}
