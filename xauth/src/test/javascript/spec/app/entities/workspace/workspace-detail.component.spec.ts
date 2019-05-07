/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { XauthTestModule } from '../../../test.module';
import { WorkspaceDetailComponent } from 'app/entities/workspace/workspace-detail.component';
import { Workspace } from 'app/shared/model/workspace.model';

describe('Component Tests', () => {
    describe('Workspace Management Detail Component', () => {
        let comp: WorkspaceDetailComponent;
        let fixture: ComponentFixture<WorkspaceDetailComponent>;
        const route = ({ data: of({ workspace: new Workspace(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [XauthTestModule],
                declarations: [WorkspaceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WorkspaceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WorkspaceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.workspace).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
