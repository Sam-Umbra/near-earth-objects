import { Component } from '@angular/core';
import { RevealDirective } from '../../../helpers/reveal.directive';

@Component({
  selector: 'app-threat-levels',
  imports: [RevealDirective],
  templateUrl: './threat-levels.html',
  styleUrl: './threat-levels.scss',
})
export class ThreatLevels {
  levels = [
    {
      tier: 'SAFE',
      risk: '0-15%',
      width: '15%',
      type: 'safe',
      detail: 'No immediate impact trajectory detected.',
    },
    {
      tier: 'CAUTION',
      risk: '15-65%',
      width: '65%',
      type: 'caution',
      detail: 'Close approach within lunar distance parameters.',
    },
    {
      tier: 'DANGER',
      risk: '65-100%',
      width: '100%',
      type: 'danger',
      detail: 'High probability of atmospheric entry or critical proximity.',
    },
  ];
}
