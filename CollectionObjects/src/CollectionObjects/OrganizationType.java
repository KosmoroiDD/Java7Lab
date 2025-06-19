package CollectionObjects;

/**
 * Перечисление OrganizationType представляет типы организаций.
 * Возможные значения:
 * <ul>
 *     <li>COMMERCIAL - коммерческая организация.</li>
 *     <li>PUBLIC - публичная организация.</li>
 *     <li>GOVERNMENT - государственная организация.</li>
 *     <li>TRUST - трастовая организация.</li>
 *     <li>PRIVATE_LIMITED_COMPANY - частная компания с ограниченной ответственностью.</li>
 * </ul>
 */
public enum OrganizationType {
    /** Коммерческая организация. */
    COMMERCIAL,

    /** Публичная организация. */
    PUBLIC,

    /** Государственная организация. */
    GOVERNMENT,

    /** Трастовая организация. */
    TRUST,

    /** Частная компания с ограниченной ответственностью. */
    PRIVATE_LIMITED_COMPANY
}