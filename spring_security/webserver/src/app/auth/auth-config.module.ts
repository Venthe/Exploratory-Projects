import {APP_INITIALIZER, NgModule} from '@angular/core';
import {AuthModule, OidcConfigService} from 'angular-auth-oidc-client';

export function configureAuth(oidcConfigService: OidcConfigService): () => Promise<any> {
  return () => oidcConfigService.withConfig({
    stsServer: 'http://localhost:8080/auth/realms/Example',
    redirectUrl: window.location.origin,
    secureRoutes: [
      '/api/v1'
    ],
    postLogoutRedirectUri: window.location.origin,
    clientId: 'example-client',
    scope: 'openid profile roles', // 'openid profile ' + your scopes
    responseType: 'code',
    silentRenew: true,
    silentRenewUrl: window.location.origin + '/silent-renew.html',
    renewTimeBeforeTokenExpiresInSeconds: 10,
  });

}

@NgModule({
  imports: [AuthModule.forRoot()],
  exports: [AuthModule],
  providers: [
    OidcConfigService,
    {
      provide: APP_INITIALIZER,
      useFactory: configureAuth,
      deps: [OidcConfigService],
      multi: true,
    },
  ],
})
export class AuthConfigModule {
}
