import { TestBed } from '@angular/core/testing';

import { WorkLog } from './work-log';

describe('WorkLog', () => {
  let service: WorkLog;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkLog);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
