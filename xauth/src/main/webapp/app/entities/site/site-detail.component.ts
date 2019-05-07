import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISite } from 'app/shared/model/site.model';

@Component({
    selector: 'jhi-site-detail',
    templateUrl: './site-detail.component.html'
})
export class SiteDetailComponent implements OnInit {
    site: ISite;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ site }) => {
            this.site = site;
        });
    }

    previousState() {
        window.history.back();
    }
}
