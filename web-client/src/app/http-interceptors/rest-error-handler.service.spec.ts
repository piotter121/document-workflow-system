import { TestBed, inject } from '@angular/core/testing';

import { RestErrorHandlerService } from './rest-error-handler.service';

describe('RestErrorHandlerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RestErrorHandlerService]
    });
  });

  it('should be created', inject([RestErrorHandlerService], (service: RestErrorHandlerService) => {
    expect(service).toBeTruthy();
  }));
});
