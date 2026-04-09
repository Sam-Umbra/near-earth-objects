import { Component } from '@angular/core';
import { Footer } from '../../components/layouts/footer/footer';
import { HeroSection } from "../../components/sections/hero-section/hero-section";
import { FeaturesSection } from "../../components/sections/features-section/features-section";
import { ArchitectureSection } from "../../components/sections/architecture-section/architecture-section";
import { ThreatLevels } from "../../components/sections/threat-levels/threat-levels";
import { TechStack } from "../../components/sections/tech-stack/tech-stack";

@Component({
  selector: 'app-landing-page',
  imports: [Footer, HeroSection, FeaturesSection, ArchitectureSection, ThreatLevels, TechStack],
  templateUrl: './landing-page.html',
  styleUrl: './landing-page.scss',
})
export class LandingPage {
  isMenuOpen = false;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  closeMenu() {
    this.isMenuOpen = false;
  }
}
