import { TestBed } from '@angular/core/testing';

import { StarRankingService } from './star-ranking.service';

describe('StarRankingService', () => {
  let service: StarRankingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StarRankingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
