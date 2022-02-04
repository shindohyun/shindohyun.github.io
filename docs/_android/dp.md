---
layout: default
title: DP
parent: Android
permalink: /docs/android/dp
nav_order: 11
---

# DP
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## DPI (Dot Per Inch)
- ldpi: 120dpi (1인치에 120픽셀)
- mdpi: 160dpi (1인치에 160픽셀)
- xxxhdpi: 640dpi (1인치에 640픽셀)

---

## DP (Density-Independent Pixel)

```
px = dp * 단말 DPI/기본 160
dp = px * 기본 160/단말 DPI
```

ex) xxxhdpi의 1px은 몇 dp? 1px * 160/640 = 0.25dp

타겟 디바이스 및 AVD 또는 개발 화면의 해상도를 확인하고 맞춰서 개발하면 편리하다.
