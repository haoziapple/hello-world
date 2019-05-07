/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ProfileService } from 'app/entities/profile/profile.service';
import { IProfile, Profile, Sex } from 'app/shared/model/profile.model';

describe('Service Tests', () => {
    describe('Profile Service', () => {
        let injector: TestBed;
        let service: ProfileService;
        let httpMock: HttpTestingController;
        let elemDefault: IProfile;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProfileService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Profile(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', Sex.MALE, false, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Profile', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Profile(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Profile', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        loginName: 'BBBBBB',
                        password: 'BBBBBB',
                        mobile: 'BBBBBB',
                        email: 'BBBBBB',
                        sex: 'BBBBBB',
                        locked: true,
                        remark: 'BBBBBB',
                        extMap: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Profile', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        loginName: 'BBBBBB',
                        password: 'BBBBBB',
                        mobile: 'BBBBBB',
                        email: 'BBBBBB',
                        sex: 'BBBBBB',
                        locked: true,
                        remark: 'BBBBBB',
                        extMap: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Profile', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
