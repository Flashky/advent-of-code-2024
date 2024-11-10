# Advent of Code {year}

## Housekeeping

Before creating a new repository from this template, perform the following steps.

### Personal Access Token Setup

Open **[Personal access tokens (classic)](https://github.com/settings/tokens)** settings and verify if there is any
``PAT_TOKEN`` or if it is nearly to expire.

#### Creation of a new token

If there is no ``PAT_TOKEN`` created:

1. Click on **Generate New Token**.
2. Select ``repo`` checkbox.
3. Copy the token.

#### Refresh of an existing token

If there is an existing ``PAT_TOKEN``:

1. Click on it.
2. If the expiration date is due soon, click on **regenerate the token**.
3. Update the ``PAT_TOKEN`` at this template repository.

### Update dependencies

Open [dependency graph](https://github.com/Flashky/advent-of-code-yyyy/network/updates) settings 
and manually run the following jobs:

- ``pom.xml``
- ``.github/workflows/build-report.yml``

Review and merge any pending pull requests.

## New repository creation

Follow this steps after housekeeping is finished.

### Creation

1. Open the main page of the [template repository](https://github.com/Flashky/advent-of-code-yyyy).
2. Click on **Use this template**.
3. Select **Create a new repository**:
   1. Give a name to the new repository. Example: ``advent-of-code-2022``.

### Personal Access Token Setup

Add the previously mentioned ``PAT_TOKEN`` to this new repository.

### Codacy Project Token Setup

1. Go to [Codacy](https://app.codacy.com) and add the repository.
2. Open **Settings / Coverage** and copy the ``CODACY_PROJECT_TOKEN`` value.
3. Open the created repository.
4. Open **Settings / Secrets and variables / Actions** and click on **New repository secret**:
   1. **Name:** ``CODACY_PROJECT_TOKEN``
   2. **Secret:** The previously copied token.
  
### Update repository information

1. Go to **Settings / Actions / General**.
2. Under **Workflow permissions**:
   1. Enable **Read and write permissions**.
   2. Enable **Allow GitHub Actions to create and approve pull requests**.
4. Go to **Actions** and open the **Update year** workflow:
   1. Click on **Run workflow**.
   2. Type the year in ``yyyy`` format and click on **Run workflow**.

After the workflow has ended a Pull Request will be created:
1. Open the [pull request](https://github.com/Flashky/advent-of-code-{year}/pull/1).
2. Verify everything is right and then **merge** it.
   
After the PR is merged the repository will have its description and topics updated. Also, all ``README.md`` and ``pom.xml`` will be updated with current year value. The PR can be declined and workflow can be executed again if there was any problem that needed a fix.

### Data Repository Setup

1. Create a private data repository using [advent-of-code-yyyy-data](https://github.com/Flashky/advent-of-code-yyyy-data) template repository and set it up.
2. Add the data repository as a [submodule](https://github.blog/2016-02-01-working-with-submodules/) using the command:

```bash
git clone https://github.com/Flashky/advent-of-code-{year}.git &&
git submodule add -b master https://github.com/Flashky/advent-of-code-{year}-data.git advent-of-code-{year}/src/test/resources/inputs && 
git push
```

### Codacy Badges Setup

1. Go to [Codacy](https://app.codacy.com) and open the repository.
2. Open **Settings / General** and copy both the ``code quality`` and ``coverage`` badges.
3. Add them to this ``README.md``.

### Almost done...

1. Remove these instructions from ``README.md``.
2. Push the changes and...

**Enjoy your new Advent of Code edition!**

---

- [Day 1](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day01)
- [Day 2](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day02)
- [Day 3](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day03)
- [Day 4](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day04)
- [Day 5](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day05)
- [Day 6](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day06)
- [Day 7](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day07)
- [Day 8](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day08)
- [Day 9](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day09)
- [Day 10](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day10)
- [Day 11](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day11)
- [Day 12](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day12)
- [Day 13](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day13)
- [Day 14](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day14)
- [Day 15](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day15)
- [Day 16](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day16)
- [Day 17](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day17)
- [Day 18](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day18)
- [Day 19](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day19)
- [Day 20](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day20)
- [Day 21](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day21)
- [Day 22](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day22)
- [Day 23](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day23)
- [Day 24](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day24)
- [Day 25](https://github.com/Flashky/advent-of-code-{year}/tree/master/src/main/java/com/adventofcode/flashk/day25)

## Cloning this repository

Without data repository:

```bash
git clone https://github.com/Flashky/advent-of-code-{year}.git
```

Including data repository:

```bash
git clone https://github.com/Flashky/advent-of-code-{year}.git --recurse-submodules
```

## About

- [Advent of Code](https://adventofcode.com/{year}/about)
