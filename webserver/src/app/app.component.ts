import {Component, OnDestroy, OnInit} from '@angular/core';
import {OidcSecurityService} from 'angular-auth-oidc-client';
import {Observable, Subscription} from 'rxjs';
import {DataService} from './data.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'webserver';

  subscription!: Subscription;
  userData!: Observable<any>;
  data!: Observable<string[]>;

  constructor(public oidcSecurityService: OidcSecurityService, private dataService: DataService) {
  }

  ngOnInit(): void {
    this.userData = this.oidcSecurityService.userData$;
    this.subscription = this.oidcSecurityService.checkAuth().subscribe((auth) => console.log('is authenticated', auth));

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getData(): void {
    this.data = this.dataService.getData().pipe(first());
  }

  login(): void {
    console.log(this.oidcSecurityService.configuration);
    this.oidcSecurityService.authorize();
  }

  logout(): void {
    this.oidcSecurityService.logoff();
  }
}
