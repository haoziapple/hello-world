/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { RoleService } from 'app/entities/role/role.service';
import { IRole, Role, ClientType, RoleType } from 'app/shared/model/role.model';

describe('Service Tests', () => {
    describe('Role Service', () => {
        let injector: TestBed;
        let service: RoleService;
        let httpMock: HttpTestingController;
        let elemDefault: IRole;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RoleService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Role(0, 0, 0, ClientType.PC, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', RoleType.SYSTEM_ADMIN);
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

            it('should create a Role', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Role(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Role', async () => {
                const returnedFromService = Object.assign(
                    {
                        spaceId: 1,
                        siteId: 1,
                        clientType: 'BBBBBB',
                        name: 'BBBBBB',
                        remark: 'BBBBBB',
                        extmap: 'BBBBBB',
                        roleType: 'BBBBBB'
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

            it('should return a list of Role', async () => {
                const returnedFromService = Object.assign(
                    {
                        spaceId: 1,
                        siteId: 1,
                        clientType: 'BBBBBB',
                        name: 'BBBBBB',
                        remark: 'BBBBBB',
                        extmap: 'BBBBBB',
                        roleType: 'BBBBBB'
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

            it('should delete a Role', async () => {
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
