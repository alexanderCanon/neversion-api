#!/bin/bash

# ==========================================
# Configuration
# ==========================================
domains=(api.neversion.com)
rsa_key_size=4096
data_path="./certbot"
email="axcanon544@gmail.com"
staging=0
# ==========================================

if [ -d "$data_path" ]; then
  read -p "Datos de Certbot existentes encontrados. ¿Continuar y reemplazar certificados existentes? (y/N) " decision
  if [ "$decision" != "Y" ] && [ "$decision" != "y" ]; then
    exit
  fi
fi

echo "### Descargando parámetros TLS recomendados ..."
mkdir -p "$data_path/conf"
curl -s https://raw.githubusercontent.com/certbot/certbot/master/certbot-nginx/certbot_nginx/_internal/tls_configs/options-ssl-nginx.conf > "$data_path/conf/options-ssl-nginx.conf"
curl -s https://raw.githubusercontent.com/certbot/certbot/master/certbot/certbot/ssl-dhparams.pem > "$data_path/conf/ssl-dhparams.pem"
echo

echo "### Creando certificado dummy para $domains ..."
path="/etc/letsencrypt/live/$domains"
mkdir -p "$data_path/conf/live/$domains"
docker compose -f compose.prod.yaml run --rm --entrypoint "\
  openssl req -x509 -nodes -newkey rsa:$rsa_key_size -days 1\
    -keyout '$path/privkey.pem' \
    -out '$path/fullchain.pem' \
    -subj '/CN=localhost'" certbot
echo

echo "### Iniciando Nginx ..."
docker compose -f compose.prod.yaml up --force-recreate -d nginx
echo

echo "### Borrando certificado dummy para $domains ..."
docker compose -f compose.prod.yaml run --rm --entrypoint "\
  rm -Rf /etc/letsencrypt/live/$domains && \
  rm -Rf /etc/letsencrypt/archive/$domains && \
  rm -Rf /etc/letsencrypt/renewal/$domains.conf" certbot
echo

echo "### Solicitando certificado Let's Encrypt para $domains ..."
domain_args=""
for domain in "${domains[@]}"; do
  domain_args="$domain_args -d $domain"
done

# Selecciona el entorno de Let's Encrypt (Staging o Producción)
case "$staging" in
  1) staging_arg="--staging";;
  0) staging_arg="";;
esac

docker compose -f compose.prod.yaml run --rm --entrypoint "\
  certbot certonly --webroot -w /var/www/certbot \
    $staging_arg \
    $domain_args \
    --email $email \
    --rsa-key-size $rsa_key_size \
    --agree-tos \
    --force-renewal" certbot
echo

echo "### Recargando Nginx ..."
docker compose -f compose.prod.yaml exec nginx nginx -s reload