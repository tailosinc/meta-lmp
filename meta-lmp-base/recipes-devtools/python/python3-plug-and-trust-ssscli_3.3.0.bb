SUMMARY = "NXP Plug and Trust SSS Python command line tool"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "git://github.com/foundriesio/plug-and-trust-ssscli;branch=main;protocol=https"
SRCREV = "6c679410e15fb2ae1123bc74ef54f7173278abcc"

S = "${WORKDIR}/git/src"

inherit setuptools3

RDEPENDS:${PN} += "plug-and-trust-seteec \
    ${PYTHON_PN}-click \
    ${PYTHON_PN}-logging \
    ${PYTHON_PN}-cryptography \
"

BBCLASSEXTEND = "native nativesdk"
