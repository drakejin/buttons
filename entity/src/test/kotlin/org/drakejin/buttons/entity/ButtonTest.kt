package org.drakejin.buttons.entity

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Button 엔티티 테스트
 *
 * Clean Code 원칙에 따른 단위 테스트
 */
class ButtonTest {

    @Test
    fun `should create button with valid data`() {
        // Arrange
        val name = "Test Button"
        val description = "Test Description"
        val createdBy = UUID.randomUUID()
        val updatedBy = UUID.randomUUID()

        // Act
        val button = Button(
            name = name,
            description = description,
            createdBy = createdBy,
            updatedBy = updatedBy
        )

        // Assert
        assertEquals(name, button.name)
        assertEquals(description, button.description)
        assertEquals(ButtonType.DEFAULT, button.type)
        assertEquals(ButtonStatus.ACTIVE, button.status)
        assertEquals(createdBy, button.createdBy)
        assertEquals(updatedBy, button.updatedBy)
        assertNotNull(button.createdAt)
        assertNotNull(button.updatedAt)
        assertNull(button.deletedAt)
        assertTrue(button.isActive())
        assertFalse(button.isDeleted())
    }

    @Test
    fun `should throw exception when name is blank`() {
        // Arrange
        val blankName = ""
        val createdBy = UUID.randomUUID()
        val updatedBy = UUID.randomUUID()

        // Act & Assert
        assertThrows<IllegalArgumentException> {
            Button(
                name = blankName,
                createdBy = createdBy,
                updatedBy = updatedBy
            )
        }
    }

    @Test
    fun `should throw exception when name exceeds 100 characters`() {
        // Arrange
        val longName = "a".repeat(101)
        val createdBy = UUID.randomUUID()
        val updatedBy = UUID.randomUUID()

        // Act & Assert
        assertThrows<IllegalArgumentException> {
            Button(
                name = longName,
                createdBy = createdBy,
                updatedBy = updatedBy
            )
        }
    }

    @Test
    fun `should activate button correctly`() {
        // Arrange
        val createdBy = UUID.randomUUID()
        val initialUpdatedBy = UUID.randomUUID()
        val button = Button(
            name = "Test Button",
            status = ButtonStatus.INACTIVE,
            createdBy = createdBy,
            updatedBy = initialUpdatedBy
        )
        val updatedBy = UUID.randomUUID()

        // Act
        val activatedButton = button.activate(updatedBy)

        // Assert
        assertEquals(ButtonStatus.ACTIVE, activatedButton.status)
        assertEquals(updatedBy, activatedButton.updatedBy)
        assertTrue(activatedButton.isActive())
    }

    @Test
    fun `should deactivate button correctly`() {
        // Arrange
        val createdBy = UUID.randomUUID()
        val initialUpdatedBy = UUID.randomUUID()
        val button = Button(
            name = "Test Button",
            status = ButtonStatus.ACTIVE,
            createdBy = createdBy,
            updatedBy = initialUpdatedBy
        )
        val updatedBy = UUID.randomUUID()

        // Act
        val deactivatedButton = button.deactivate(updatedBy)

        // Assert
        assertEquals(ButtonStatus.INACTIVE, deactivatedButton.status)
        assertEquals(updatedBy, deactivatedButton.updatedBy)
        assertFalse(deactivatedButton.isActive())
    }

    @Test
    fun `should update button info correctly`() {
        // Arrange
        val createdBy = UUID.randomUUID()
        val initialUpdatedBy = UUID.randomUUID()
        val button = Button(
            name = "Old Name",
            description = "Old Description",
            createdBy = createdBy,
            updatedBy = initialUpdatedBy
        )
        val newName = "New Name"
        val newDescription = "New Description"
        val updatedBy = UUID.randomUUID()

        // Act
        val updatedButton = button.updateInfo(newName, newDescription, updatedBy)

        // Assert
        assertEquals(newName, updatedButton.name)
        assertEquals(newDescription, updatedButton.description)
        assertEquals(updatedBy, updatedButton.updatedBy)
    }

    @Test
    fun `should change button type correctly`() {
        // Arrange
        val createdBy = UUID.randomUUID()
        val initialUpdatedBy = UUID.randomUUID()
        val button = Button(
            name = "Test Button",
            type = ButtonType.DEFAULT,
            createdBy = createdBy,
            updatedBy = initialUpdatedBy
        )
        val newType = ButtonType.PRIMARY
        val updatedBy = UUID.randomUUID()

        // Act
        val updatedButton = button.changeType(newType, updatedBy)

        // Assert
        assertEquals(newType, updatedButton.type)
        assertEquals(updatedBy, updatedButton.updatedBy)
    }

    @Test
    fun `should soft delete button correctly`() {
        // Arrange
        val createdBy = UUID.randomUUID()
        val initialUpdatedBy = UUID.randomUUID()
        val button = Button(
            name = "Test Button",
            status = ButtonStatus.ACTIVE,
            createdBy = createdBy,
            updatedBy = initialUpdatedBy
        )
        val updatedBy = UUID.randomUUID()

        // Act
        val deletedButton = button.softDelete(updatedBy)

        // Assert
        assertEquals(ButtonStatus.DELETED, deletedButton.status)
        assertEquals(updatedBy, deletedButton.updatedBy)
        assertNotNull(deletedButton.deletedAt)
        assertTrue(deletedButton.isDeleted())
        assertFalse(deletedButton.isActive())
    }

    @Test
    fun `should throw exception when updating with blank name`() {
        // Arrange
        val createdBy = UUID.randomUUID()
        val initialUpdatedBy = UUID.randomUUID()
        val button = Button(
            name = "Test Button",
            createdBy = createdBy,
            updatedBy = initialUpdatedBy
        )
        val updatedBy = UUID.randomUUID()

        // Act & Assert
        assertThrows<IllegalArgumentException> {
            button.updateInfo("", "New Description", updatedBy)
        }
    }

    @Test
    fun `should return false for isActive when button is deleted`() {
        // Arrange
        val createdBy = UUID.randomUUID()
        val initialUpdatedBy = UUID.randomUUID()
        val button = Button(
            name = "Test Button",
            createdBy = createdBy,
            updatedBy = initialUpdatedBy
        )
        val updatedBy = UUID.randomUUID()

        // Act
        button.softDelete(updatedBy)

        // Assert
        assertFalse(button.isActive())
        assertTrue(button.isDeleted())
    }
}
