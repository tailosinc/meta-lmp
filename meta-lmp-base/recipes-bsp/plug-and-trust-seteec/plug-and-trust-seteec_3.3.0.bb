DESCRIPTION = "NXP Plug and Trust Middleware with SETEEC support"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "git://github.com/foundriesio/plug-and-trust-seteec;branch=main;protocol=https"
SRCREV = "f9df65c413b64784fc096bb19b2b25c3b37b498c"

DEPENDS = "openssl optee-client"

inherit cmake dos2unix pkgconfig

S = "${WORKDIR}/git"

EL2GO_HOSTNAME ?= "DEFAULTHOSTNAME"

# Similar to optee-os-fio-se05x.inc
python () {
    oefid = d.getVar("SE05X_OEFID", "0xA1F4")
    if oefid in ["0xA565", "0xA564"]:
        d.setVar('SE05X_VER', "06_00")
    else:
        d.setVar('SE05X_VER', "03_XX")
}

EXTRA_OECMAKE += "\
    -DWithSharedLIB=ON -DCMAKE_BUILD_TYPE=Debug -DHost=iMXLinux -DHostCrypto=OPENSSL \
    -DSMCOM=T1oI2C -DSE05X_Auth=None -DApplet=SE05X_C -DSE05X_Ver=${SE05X_VER} \
    -DOPENSSL_INSTALL_PREFIX=${WORKDIR}/recipe-sysroot/usr \
    -DOPENSSL_ROOT_DIR=${WORKDIR}/recipe-sysroot/usr \
    -DPAHO_ENABLE_TESTING=FALSE -DPAHO_ENABLE_CPACK=FALSE -DPAHO_WITH_SSL=TRUE \
    -DWithSETEEC=ON -DEL2GO_HOSTNAME="${EL2GO_HOSTNAME}" \
"

FILES:${PN} += "\
    ${libdir}/liba7x_utils.so \
    ${libdir}/libex_common.so \
    ${libdir}/libmwlog.so \
    ${libdir}/libnxp_iot_agent_common.so \
    ${libdir}/libnxp_iot_agent.so \
    ${libdir}/libse05x.so \
    ${libdir}/libsmCom.so \
    ${libdir}/libSSS_APIs.so \
    ${libdir}/libSEMS_LITE_AGENT_APIs.so \
    ${libdir}/libsssapisw.so \
    ${libdir}/engines-3 \
"
FILES:${PN}-dev = "${includedir} ${libdir}/libpaho*.so ${libdir}/cmake ${datadir}/cmake ${datadir}/se05x"
