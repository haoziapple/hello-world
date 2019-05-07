import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAuth } from 'app/shared/model/auth.model';

type EntityResponseType = HttpResponse<IAuth>;
type EntityArrayResponseType = HttpResponse<IAuth[]>;

@Injectable({ providedIn: 'root' })
export class AuthService {
    public resourceUrl = SERVER_API_URL + 'api/auths';

    constructor(protected http: HttpClient) {}

    create(auth: IAuth): Observable<EntityResponseType> {
        return this.http.post<IAuth>(this.resourceUrl, auth, { observe: 'response' });
    }

    update(auth: IAuth): Observable<EntityResponseType> {
        return this.http.put<IAuth>(this.resourceUrl, auth, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAuth>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAuth[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
