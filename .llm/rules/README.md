# LLM Rules for WhatsApp Android Prototype

This directory contains structured instructions for LLMs working on this codebase.

## File Organization

### Core Project Information
- **00-project-overview.md** - Project overview, tech stack, build commands
- **01-architecture.md** - Folder structure, data layer, UI architecture
- **02-wds-design-system.md** - WhatsApp Design System (WDS) overview

### Mandatory Rules
- **10-mandatory-wds-usage.md** - **MUST READ** - Always use WdsTheme, never hardcode values
- **11-code-patterns.md** - Common patterns for screens, lists, navigation
- **12-component-examples.md** - Usage examples for all WDS components

### Development Guidelines
- **20-development-principles.md** - Code style, performance, security, database operations
- **21-testing-previews.md** - Testing strategy and preview functions
- **30-figma-design-implementation.md** - Guidelines for implementing Figma designs

## Quick Start

1. **Read these first (in order)**:
   - 00-project-overview.md
   - 10-mandatory-wds-usage.md

2. **Reference as needed**:
   - 02-wds-design-system.md - For available design tokens
   - 11-code-patterns.md - For implementation patterns
   - 12-component-examples.md - For component usage

3. **Follow when applicable**:
   - 20-development-principles.md - For best practices
   - 21-testing-previews.md - When writing tests
   - 30-figma-design-implementation.md - When implementing designs

## Key Principles

1. **NEVER hardcode values** - Always use WdsTheme tokens
2. **Cache theme lookups** - Store in local variables for performance
3. **Use WDS components** - Prefer over Material3 when available
4. **Follow patterns** - Maintain consistency with existing code
5. **Test thoroughly** - Include previews for all composables
