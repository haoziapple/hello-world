import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfile } from 'app/shared/model/profile.model';

@Component({
    selector: 'jhi-profile-detail',
    templateUrl: './profile-detail.component.html'
})
export class ProfileDetailComponent implements OnInit {
    profile: IProfile;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ profile }) => {
            this.profile = profile;
        });
    }

    previousState() {
        window.history.back();
    }
}
