import { Component, OnInit, ViewEncapsulation, EventEmitter, Output, Input, ChangeDetectorRef } from '@angular/core';
import { Observable, Subject } from 'rxjs'
import { map, distinctUntilChanged, filter, tap } from 'rxjs/operators'
import _ from "lodash"

enum AppId {
  ANGULAR = "angular"
}

interface MyEvent {
  type: "ping";
  payload: {
    id: AppId | any;
    text: string;
  }
}

@Component({
  selector: 'app-ng-ping',
  templateUrl: './ng-ping.component.html',
  styleUrls: ['./ng-ping.component.scss'],
  encapsulation: ViewEncapsulation.ShadowDom
})
export class NgPingComponent implements OnInit {

  inputText: string;
  store$: Observable<MyEvent[] | any>;
  dispatch: (event: MyEvent) => void;

  @Output() pingEmitter = new EventEmitter<MyEvent>();
  @Input() storeKey: string;
  @Input() dispatchKey: string;


  constructor(private readonly ref: ChangeDetectorRef) {}
  
  getCurrentEvent(store: MyEvent[]): MyEvent {
    return store.slice(-1)[0];
  }

  getDisabled(store: MyEvent[]) {
    if (store.length === 0)
      return false;
    return this.getCurrentEvent(store).payload.id === AppId.ANGULAR;
  }

  ngOnInit() {
    const subject = new Subject();
    this.store$ = subject.pipe(
      filter(el => el !== undefined && el !== null),
      tap(() => {
        this.ref.markForCheck();
      }),
      map(list => _(list).filter(el => (el.type === "ping")).value()));
    setInterval(() => {
      const state = _.get(window, this.storeKey);
      subject.next(state);
    }, 100);


    this.dispatch = _.get(window, this.dispatchKey)
  }

  emitPingEvent(text) {
    this.pingEmitter.emit({
      type: "ping",
      payload: {
        text,
        id: AppId.ANGULAR
      }
    });
  }

  emitPingEventAsDispatch(text) {
    this.dispatch({
      type: "ping",
      payload: {
        text,
        id: AppId.ANGULAR
      }
    });
  }

}
