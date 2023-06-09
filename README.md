# HWP converter API server

## Overview
본 프로젝트는 [hwplib](https://github.com/neolord0/hwplib)을 기반으로 HWP 파일을 텍스트로 바꾸어 전송해주는 API 서버입니다. 
Javalin과 Kotlin을 이용해서 간단한 API 서버를 구축하였습니다.

## Install
### Docker
- [Docker Hub](https://hub.docker.com/r/vkehfdl1/hwp-converter-api)
```bash
docker run -it -d -p 7000:7000 vkehfdl1/hwp-converter-api:1.0.0
```

## Usage
### Hwp 파일 변환
표 등을 포함하여 HWP 파일을 텍스트로 변환하는 경우.
```bash
curl -X POST -F 'file=@/path/to/file.hwp' http://localhost:7000/upload?option=all
```

표 등을 제외하고 HWP 파일을 텍스트로 변환하는 경우.
```bash
curl -X POST -F 'file=@/path/to/file.hwp' http://localhost:7000/upload?option=main-only
```

## Limitation
- hwpx 파일은 지원하지 않습니다. hwp 파일로 변환하여 시도해주세요.
- 표 등에 들어간 텍스트는 표 형식으로 동일하게 출력되지는 않습니다.
