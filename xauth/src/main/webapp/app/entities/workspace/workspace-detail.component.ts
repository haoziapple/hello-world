import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkspace } from 'app/shared/model/workspace.model';

@Component({
    selector: 'jhi-workspace-detail',
    templateUrl: './workspace-detail.component.html'
})
export class WorkspaceDetailComponent implements OnInit {
    workspace: IWorkspace;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ workspace }) => {
            this.workspace = workspace;
        });
    }

    previousState() {
        window.history.back();
    }
}
