import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injector } from '@angular/core';
import { createCustomElement } from '@angular/elements';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { NgPingComponent } from './ng-ping/ng-ping.component';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    NgPingComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ButtonModule,
    InputTextModule,
    FormsModule
  ],
  providers: [],
  entryComponents: [NgPingComponent]
})
export class AppModule {

  constructor(private injector: Injector) { }

  ngDoBootstrap() {
    // using createCustomElement from angular package it will convert angular component to stander web component
    const el = createCustomElement(NgPingComponent, {
      injector: this.injector
    });
    // using built in the browser to create your own custome element name
    customElements.define('ng-ping', el);
  }

}
