import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITemplate } from 'app/shared/model/template.model';

@Component({
    selector: 'jhi-template-detail',
    templateUrl: './template-detail.component.html'
})
export class TemplateDetailComponent implements OnInit {
    template: ITemplate;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ template }) => {
            this.template = template;
        });
    }

    previousState() {
        window.history.back();
    }
}
