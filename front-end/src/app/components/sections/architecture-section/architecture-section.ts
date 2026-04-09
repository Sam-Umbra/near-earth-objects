import { Component } from '@angular/core';
import { RevealDirective } from '../../../helpers/reveal.directive';

export interface ArchLayer {
  label: string;
  sub: string;
  detail: string;
  color: 'primary' | 'accent';
}

@Component({
  selector: 'app-architecture-section',
  imports: [RevealDirective],
  templateUrl: './architecture-section.html',
  styleUrl: './architecture-section.scss',
})
export class ArchitectureSection {
  layers: ArchLayer[] = [
    {
      label: 'NASA NeoWs API',
      sub: 'Data Source',
      detail: 'Raw Near-Earth Object orbital data fetched and standardised by the Java API.',
      color: 'primary',
    },
    {
      label: 'Java API → FastAPI ML',
      sub: 'Risk Classification',
      detail:
        'Java forwards normalized payload to the Python/FastAPI microservice where the trained classifier scores collision probability.',
      color: 'accent',
    },
    {
      label: 'Java API — Cleanse & Store',
      sub: 'Post-Processing',
      detail:
        'Prediction results returned to Java, cleaned, validated, and persisted before broadcasting.',
      color: 'primary',
    },
    {
      label: 'WebSocket Broadcast',
      sub: 'Real-Time Distribution',
      detail:
        'Spring Boot pushes enriched NEO records to all subscribed clients the instant processing completes — zero polling.',
      color: 'accent',
    },
    {
      label: 'Angular Dashboard',
      sub: 'Client Display',
      detail:
        'Frontend receives the live stream and renders risk indicators, approach timelines, and stat cards in real time.',
      color: 'primary',
    },
  ];
}
