import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuth } from 'app/shared/model/auth.model';

@Component({
    selector: 'jhi-auth-detail',
    templateUrl: './auth-detail.component.html'
})
export class AuthDetailComponent implements OnInit {
    auth: IAuth;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ auth }) => {
            this.auth = auth;
        });
    }

    previousState() {
        window.history.back();
    }
}
