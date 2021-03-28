import {Component} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  template: `
    Unauthorized
  `,
})
export class UnauthorizedComponent {
  title = 'unauthorized';
}
