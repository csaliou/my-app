import { TestBed } from '@angular/core/testing';

import { UnServiceService } from './un-service.service';

describe('UnServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UnServiceService = TestBed.get(UnServiceService);
    expect(service).toBeTruthy();
  });
});
