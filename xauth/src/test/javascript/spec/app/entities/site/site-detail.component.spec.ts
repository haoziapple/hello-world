/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { XauthTestModule } from '../../../test.module';
import { SiteDetailComponent } from 'app/entities/site/site-detail.component';
import { Site } from 'app/shared/model/site.model';

describe('Component Tests', () => {
    describe('Site Management Detail Component', () => {
        let comp: SiteDetailComponent;
        let fixture: ComponentFixture<SiteDetailComponent>;
        const route = ({ data: of({ site: new Site(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [XauthTestModule],
                declarations: [SiteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SiteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SiteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.site).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
