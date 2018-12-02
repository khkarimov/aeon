FROM ubuntu

RUN apt-get update
RUN apt-get install wget -y
RUN apt-get install libxss1 libappindicator1 libindicator7 -y
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt install ./google-chrome*.deb -y

ENV HOME=/root \
	DEBIAN_FRONTEND=noninteractive \
	LANG=en_US.UTF-8 \
	LANGUAGE=en_US.UTF-8 \
	LC_ALL=C.UTF-8 \
	DISPLAY=:0.0 \
	DISPLAY_WIDTH=1024 \
	DISPLAY_HEIGHT=768

# Install git, supervisor, VNC, & X11 packages
RUN apt-get install \
	fluxbox \
	git \
	socat \
	supervisor \
    default-jre \
	x11vnc \
	xvfb \
    gettext \
    nginx -y

# Clone noVNC from github
RUN git clone  https://github.com/kanaka/noVNC.git /root/noVNC \
	&& rm -rf /root/noVNC/.git

COPY core_nginx.conf /etc/nginx/nginx.conf
COPY nginx.conf /etc/nginx/conf.d/
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY lib lib
COPY plugins plugins
COPY Aeon.Platform.Http-1.14.4-all.jar aeon.jar

# Modify the launch script 'ps -p'
RUN sed -i -- "s/ps -p/ps -o pid | grep/g" /root/noVNC/utils/launch.sh

EXPOSE 8080

ENV PORT 8080
RUN addgroup nginx
RUN adduser nginx --ingroup nginx --disabled-password
CMD export uri="\$uri" \
    export http_upgrade="\$http_upgrade" \
    && /usr/bin/supervisord -c /etc/supervisor/conf.d/supervisord.conf
